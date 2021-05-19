import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {filter, takeUntil} from 'rxjs/operators';
import {ReplaySubject} from 'rxjs';

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

  constructor(private router: Router) {
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
    // console.log();
    // const t = this.value4.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})$/i);
    // ^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})
    // console.log(t);
  }

}
