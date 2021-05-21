import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthentificationService} from '../../../core/services/authentification.service';
import {RoleType} from '@app/core/enum/role-type.enum';
import {UserInfoDTO} from '@app/shared/models';
import {UserService} from '@app/core/services/user.service';
import {ConfirmationDialogComponent} from '@app/shared/components/confirmation-dialog/confirmation-dialog.component';
import {EToastSeverities, ToastService} from '@app/core/services';
import {DialogService} from 'primeng/dynamicdialog';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userRole: string;
  username: string;
  user: UserInfoDTO;

  constructor(
    public authService: AuthentificationService,
    public toastService: ToastService,
    private readonly dialogService: DialogService,
    private userService: UserService,
    private router: Router
  ) {
  }


  ngOnInit(): void {
    this.getUserRole();
    this.getUsername();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  async onLogOutClick(): Promise<void> {
    this.authService.logout();
    await this.router.navigate(['/login']);
    this.reload();
  }

  getUserRole(): void {
    const adminRole: string = this.authService.currentTokenRoleValue;
    if (adminRole === RoleType.ADMIN) {
      this.userRole = 'Admin';
    } else {
      this.userRole = 'Client';
    }
  }

  getUsername(): void {
    this.userService.getUserByJwt()
      .subscribe(
        (data: UserInfoDTO) => {
          this.username = data.username;
          this.user = data;
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
