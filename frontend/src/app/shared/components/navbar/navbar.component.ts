import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthentificationService} from '../../../core/services/authentification.service';
import {RoleType} from '@app/core/enum/role-type.enum';
import {UserInfoDTO} from '@app/shared/models';
import {UserService} from '@app/core/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userRole: string;
  username: string;

  constructor(
    public authService: AuthentificationService,
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
        },
        error => {
          console.error(error);
        });
  }

}
