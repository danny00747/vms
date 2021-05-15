import {Component, OnInit} from '@angular/core';
import {DynamicDialogConfig} from 'primeng/dynamicdialog';
import {UserInfoDTO} from '@app/shared/models';

@Component({
  selector: 'app-view-booking',
  templateUrl: './view-booking.component.html',
  styleUrls: ['./view-booking.component.css']
})
export class ViewBookingComponent implements OnInit {

  bookingDetails: UserInfoDTO;

  constructor(private config: DynamicDialogConfig) {
  }

  ngOnInit(): void {
    this.bookingDetails = this.config.data;
  }

}
