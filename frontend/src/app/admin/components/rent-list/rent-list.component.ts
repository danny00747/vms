import {Component, Input, OnInit} from '@angular/core';
import {UserInfoDTO} from '@app/shared/models';
import {UserService} from '@app/core/services/user.service';

@Component({
  selector: 'app-rent-list',
  templateUrl: './rent-list.component.html',
  styleUrls: ['./rent-list.component.css']
})
export class RentListComponent implements OnInit {

  productDialog: boolean;

  products: any[];
  users: UserInfoDTO[] = [];

  product: any;

  selectedProducts: any[];

  submitted: boolean;

  statuses: any[];

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.getUsers();
    this.statuses = [
      {label: 'INSTOCK', value: 'instock'},
      {label: 'LOWSTOCK', value: 'lowstock'},
      {label: 'OUTOFSTOCK', value: 'outofstock'}
    ];
  }

  getUsers(): void {
    /*
    this.userService.getAllUsers()
      .subscribe(
        (data: UserInfoDTO[]) => {
          // console.log(this.users.length);
          const d =  data
            .filter(u => u.bookingDTO && u.bookingDTO.rentDTO);
          this.users.forEach(x => {
            // x.bookingDTO.rentDTO.daysDiff = this.daysDiff(x.bookingDTO.withdrawalDate, x.bookingDTO.returnDate);
           // x.bookingDTO.rentDTO.costToPay = x.bookingDTO.rentDTO.daysDiff * x.bookingDTO.carDTO.modelDTO.princingDetailsDTO.costPerDay;
          });
          console.log(d);
        },
        error => {
          console.error(error);
        });
     */
  }

  daysDiff(d1: Date, d2: Date): number {
    const date1 = new Date(d1);
    const date2 = new Date(d2);
    const diffTime = Math.abs(date2.getTime() - date1.getTime());
    return Math.floor(diffTime / (1000 * 60 * 60 * 24));
  }

}
