import { Component, OnInit } from '@angular/core';
import {UserService} from '@app/core/services/user.service';
import {NavigationStart, Router} from '@angular/router';
import {filter} from 'rxjs/operators';
import {UserInfoDTO} from '@app/shared/models';

@Component({
  selector: 'app-reservation-recap',
  templateUrl: './reservation-recap.component.html',
  styleUrls: ['./reservation-recap.component.css']
})
export class ReservationRecapComponent implements OnInit {

  user: UserInfoDTO;

  constructor(
    private userService: UserService,
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
        },
        error => {
          console.error(error);
        });
  }

}
