import {Component, OnInit} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {filter} from 'rxjs/operators';
import {EToastSeverities, ToastService} from '@app/core/services';
import {UserService} from '@app/core/services/user.service';
import {ContactMessageDTO} from '@app/shared/models';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  firstName: string;
  lastName: string;
  phoneNumber: string;
  email: string;
  message: string;

  constructor(private router: Router,
              private readonly toast: ToastService,
              private userService: UserService,
              public toastService: ToastService) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  onSubmit(): void {
    const contactMessageDTO: ContactMessageDTO = {
      firstName: this.firstName,
      lastName: this.lastName,
      phoneNumber: this.phoneNumber,
      email: this.email,
      message: this.message
    };

    this.userService.sendContactMessage(contactMessageDTO)
      .subscribe(
         () => {
          this.toastService.show(EToastSeverities.SUCCESS, 'Your message was sent successfully !');
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });
  }

}
