import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {filter, takeUntil} from 'rxjs/operators';
import {ReplaySubject} from 'rxjs';
import {DialogService} from 'primeng/dynamicdialog';
import {EditProfileComponent} from '@app/features/components/profile/edit-profile/edit-profile.component';
import {UserInfoDTO} from '@app/shared/models';
import {UserService} from '@app/core/services/user.service';
import {ConfirmationDialogComponent} from '@app/shared/components/confirmation-dialog/confirmation-dialog.component';
import {EToastSeverities, ToastService} from '@app/core/services';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, OnDestroy {

  user: UserInfoDTO;

  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(private router: Router,
              private userService: UserService,
              public toastService: ToastService,
              private readonly dialogService: DialogService) {
    this.router.events
      .pipe(takeUntil(this.destroyed$),
        filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
    this.getLoggedInUser();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  getLoggedInUser(): void {
    this.userService.getUserByJwt()
      .subscribe(
        (data: UserInfoDTO) => {
          this.user = data;
        },
        error => {
          console.error(error);
        });
  }

  openEditProfileDialog(): void {
    const ref = this.dialogService.open(EditProfileComponent, {
      header: 'Edit your profile',
      width: '30%',
      data: {
        user: this.user
      }
    });

    ref.onClose.subscribe((data: UserInfoDTO) => {
      if (data) {
        this.toastService.show(EToastSeverities.SUCCESS, 'Successfully updated !');
        this.user = data;
      }
    });
  }

  logOutUser(): void {
    const ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: 'Confirmation',
      data: {
        message: 'You are about to log out, do you want to continue ? '
      }
    });
    ref.onClose.subscribe((confirm: boolean) => {
      if (confirm) {
      }
    });
  }

  cancelReservation(): void {
    const ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: 'Confirmation',
      data: {
        message: 'You are about to cancel this reservation, this action is irreversible !'
      }
    });
    ref.onClose.subscribe((confirm: boolean) => {
      if (confirm) {
      }
    });
  }

  @HostListener('window:beforeunload')
  async ngOnDestroy(): Promise<any> {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

}
