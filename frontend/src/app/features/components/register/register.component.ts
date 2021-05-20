import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {filter, takeUntil} from 'rxjs/operators';
import {ReplaySubject} from 'rxjs';
import {EToastSeverities, ToastService} from '@app/core/services';
import {UserService} from '@app/core/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {

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
  emailKey = '';
  phoneCode: number;

  verifiedEmailKey = false;
  verifiedPhoneCode = false;

  value4: string;

  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(private router: Router,
              public toastService: ToastService,
              private userService: UserService) {
    this.router.events
      .pipe(takeUntil(this.destroyed$),
        filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  @HostListener('window:beforeunload')
  async ngOnDestroy(): Promise<any> {
    this.destroyed$.next(true);
    this.destroyed$.complete();
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
          this.toastService.show(EToastSeverities.SUCCESS, 'Your phone number has been successfully verified.');
          this.verifiedPhoneCode = true;
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });
  }

  checkBooking(): boolean {
    return this.verifiedEmailKey && this.verifiedPhoneCode;
  }

  signUp(): void {
    this.userService.activateUser(this.username)
      .subscribe(
        async () => {
          this.toastService.show(EToastSeverities.SUCCESS, 'Your account has been successfully activated. !');
          setTimeout(async () => await this.router.navigate(['/login']), 2000);
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });
  }
}
