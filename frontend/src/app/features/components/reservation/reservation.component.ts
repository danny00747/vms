import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {filter} from 'rxjs/operators';
import {CarDTO, LoginDTO} from '@app/shared/models';
import {CarService} from '@app/core/services/car.service';
import {SelectItem} from 'primeng/api';
import {AuthentificationService, EToastSeverities, ToastService} from '@app/core/services';
import {UserService} from '@app/core/services/user.service';
import {BookingService} from '@app/core/services/booking.service';
import {BookingDTO} from '@app/shared/models/booking';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  username: string;
  userEmail: string;
  password: string;
  phoneNumber: number;

  road: string;
  houseNumber: number;
  townName: string;
  postCode: number;
  passwordPattern = '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})';

  verifyInputs = false;
  emailKey: string;
  phoneCode: number;

  verifiedEmailKey = false;
  verifiedPhoneCode = false;

  carId: string;
  car: CarDTO;

  loggedInUser = !!JSON.parse(localStorage.getItem('currentUser'))?.token;

  acceptedTerms = false;
  successBooking = false;

  withdrawalDate: Date;
  returnDate: Date;
  minDate: Date = new Date();
  defaultHour = new Date().getHours() + ':' + new Date().getMinutes();

  constructor(private router: Router,
              private route: ActivatedRoute,
              public toastService: ToastService,
              private userService: UserService,
              private bookingService: BookingService,
              private authService: AuthentificationService,
              private carService: CarService) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
    this.carId = this.route.snapshot.paramMap.get('carId');
    this.getCarDetails();
    // this.getLoggedInUser();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  getCarDetails(): void {
    this.carService.getOneCar(this.carId)
      .subscribe(
        async (data: CarDTO) => {
          this.car = data;
        },
        error => {
          console.error(error);
        });
  }

  submit(): void {
    const createdUser: any = {
      username: this.username,
      userEmail: this.userEmail,
      password: this.password,
      phoneNumber: this.phoneNumber.toString(),
      addressDTO: {
        road: this.road,
        postBox: 3,
        houseNumber: this.houseNumber,
        townDTO: {
          postcode: this.postCode.toString(),
          name: this.townName
        }
      }
    };

    this.userService.createUser(createdUser)
      .subscribe(
        data => {
          this.toastService.show(EToastSeverities.INFO, data.message);
          this.verifyInputs = true;
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });
  }

  checkEmailKey(): boolean {
    return this.emailKey !== undefined && this.emailKey.length === 36;
  }

  checkPhoneCode(): boolean {
    return this.phoneCode !== undefined && this.phoneCode.toString().length === 7;
  }

  verifyEmailKey(): void {
    this.userService.verifyEmail(this.emailKey)
      .subscribe(
        () => {
          this.toastService.show(EToastSeverities.SUCCESS, 'Your email has been successfully verified. !');
          this.verifiedEmailKey = true;
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });
  }

  verifyPhoneCode(): void {
    this.userService.verifyPhoneCode(this.phoneCode)
      .subscribe(
        () => {
          this.toastService.show(EToastSeverities.SUCCESS, 'Your phone number has been successfully verified. !');
          this.verifiedPhoneCode = true;
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });
  }

  checkAcceptedTerms(target: any): void {
    this.acceptedTerms = target.checked;
  }

  checkBooking(): boolean {
    const checkDates: any = this.withdrawalDate && this.returnDate;
    return this.verifiedEmailKey && this.verifiedPhoneCode && this.acceptedTerms && checkDates;
  }

  checkBookingForLoggedInUser(): boolean {
    const checkDates: any = this.withdrawalDate && this.returnDate;
    return this.acceptedTerms && checkDates;
  }

  async saveBooking(): Promise<void> {
    const editReturnDate = this.returnDate;

    (typeof this.defaultHour === 'string') ?
      editReturnDate.setHours(Number(this.defaultHour.split(':')[0]), Number(this.defaultHour.split(':')[1])) :
      editReturnDate.setHours(new Date(this.defaultHour).getHours(), new Date(this.defaultHour).getMinutes());

    this.userService.activateUser(this.username).toPromise().then().catch();

    this.userService.activateUser(this.username)
      .subscribe(
        () => {
          this.signInUser(editReturnDate);
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });


  }

  signInUser(editedReturnDate: Date): void {
    const loginForm: LoginDTO = {
      pseudo: this.username,
      password: this.password
    };
    this.authService.loginUser(loginForm)
      .subscribe(
        () => {
          this.createBooking(editedReturnDate);
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });
  }

  createBooking(editedReturnDate: Date): void {
    const booking = {
      withdrawalDate: this.withdrawalDate,
      returnDate: editedReturnDate
    };

    this.bookingService.createBooking(this.carId, booking)
      .subscribe(
        (data: BookingDTO) => {
          this.toastService.show(EToastSeverities.SUCCESS, 'We have successfully received your reservation. !');
          this.successBooking = true;
          setTimeout(async () => await  this.router.navigate(['/reservation/recap/', data.bookingId]), 1000);
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });
  }

   saveBooking2(): void {
    const editReturnDate = this.returnDate;

    (typeof this.defaultHour === 'string') ?
      editReturnDate.setHours(Number(this.defaultHour.split(':')[0]), Number(this.defaultHour.split(':')[1])) :
      editReturnDate.setHours(new Date(this.defaultHour).getHours(), new Date(this.defaultHour).getMinutes());

    this.createBooking(editReturnDate);

  }
}
