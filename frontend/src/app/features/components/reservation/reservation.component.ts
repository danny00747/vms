import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {filter, takeUntil} from 'rxjs/operators';
import {CarDTO, CreateUserDTO, UserDTO} from '@app/shared/models';
import {CarService} from '@app/core/services/car.service';
import {SelectItem} from 'primeng/api';
import {EToastSeverities, ToastService} from '@app/core/services';
import {UserService} from '@app/core/services/user.service';

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

  date1: Date;
  date2: Date;
  date3: Date;
  date4: Date;
  minDate: Date = new Date();
  defaultHour = new Date().getHours() + ':' + new Date().getMinutes();

  sortOptions: SelectItem[] = [
    {label: 'Opel', value: 'Opel'},
    {label: 'Ford', value: 'Ford'},
    {label: 'Renault', value: 'Renault'},
    {label: 'Toyota', value: 'Toyota'},
    {label: 'Volkswagen', value: 'Volkswagen'}
  ];

  constructor(private router: Router,
              private route: ActivatedRoute,
              public toastService: ToastService,
              private userService: UserService,
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
          console.log(data);
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
    this.userService.verifyEmailKey(this.emailKey)
      .subscribe(
        () => {
          this.toastService.show(EToastSeverities.INFO, 'Your account has been successfully activated.');
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
          this.toastService.show(EToastSeverities.INFO, 'Your phone number has been successfully verified.');
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

  checkBooking(): boolean{
    return this.verifiedEmailKey && this.verifiedPhoneCode && this.acceptedTerms;
  }
}
