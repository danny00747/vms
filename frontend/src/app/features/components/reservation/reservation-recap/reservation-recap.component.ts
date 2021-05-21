import { Component, OnInit } from '@angular/core';
import {UserService} from '@app/core/services/user.service';
import {NavigationStart, Router} from '@angular/router';
import {filter} from 'rxjs/operators';
import {UserInfoDTO} from '@app/shared/models';
import {EToastSeverities, ToastService} from '@app/core/services';
import {ConfirmationDialogComponent} from '@app/shared/components/confirmation-dialog/confirmation-dialog.component';
import {DialogService} from 'primeng/dynamicdialog';

@Component({
  selector: 'app-reservation-recap',
  templateUrl: './reservation-recap.component.html',
  styleUrls: ['./reservation-recap.component.css']
})
export class ReservationRecapComponent implements OnInit {

  user: UserInfoDTO;
  diffDays = 0;

  constructor(
    private userService: UserService,
    public toastService: ToastService,
    private readonly dialogService: DialogService,
    private router: Router) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
    this.getUserInfo();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  getUserInfo(): void {
    this.userService.getUserByJwt()
      .subscribe(
        (data: UserInfoDTO) => {
          this.user = data;
          const date1 = new Date(this.user.bookingDTO.withdrawalDate);
          const date2 = new Date(this.user.bookingDTO.returnDate);
          const diffTime = Math.abs(date2.getTime() - date1.getTime());
          this.diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));
        },
        error => {
          console.error(error);
        });
  }

  confirmCancel(): void {
    const ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: 'Confirmation',
      data: {
        message: 'Are you sure that you want to cancel this reservation?'
      }
    });
    ref.onClose.subscribe((confirm: boolean) => {
      if (confirm) {
        this.toastService.show(EToastSeverities.SUCCESS, 'cancelled !');
      }
    });
  }

}
