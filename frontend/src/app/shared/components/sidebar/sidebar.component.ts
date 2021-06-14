import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {UserService} from '@app/core/services/user.service';
import {UserInfoDTO} from '@app/shared/models';
import {Router} from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  pickUpDate: Date;
  returnDate: Date;
  minDate: Date = new Date();
  rangeValues: number[] = [110, 249];
  user: UserInfoDTO;

  @Output() closeSideBar = new EventEmitter<boolean>();

  constructor(
    private router: Router,
    private userService: UserService) { }

  ngOnInit(): void {
    this.getUser();
  }

  getUser(): void {
    this.userService.getUserByJwt()
      .subscribe(
        (data: UserInfoDTO) => {
          this.user = data;
        },
        error => {
          console.error(error);
        });
  }

  checkSubmission(): boolean {
    return this.returnDate?.getTime() - this.pickUpDate?.getTime() > 1;
  }

  async submitQuery(): Promise<void> {
    await this.router.navigate(['/cars'],
      {
        queryParams:
          {
            pickUpDate: this.pickUpDate.toISOString(),
            costRange: this.rangeValues
          }
      });
  }

  sideBarClose(): void {
    this.closeSideBar.emit(false);
  }
}
