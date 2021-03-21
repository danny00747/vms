import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthentificationService} from '../../../core/services/authentification.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {


  constructor(
    public authService: AuthentificationService,
    private router: Router
  ) {
  }


  ngOnInit(): void {
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  async onLogOutClick(): Promise<void> {
    this.authService.logout();
    await this.router.navigate(['/login']);
    this.reload();
  }

}
