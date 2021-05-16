import {Component, OnInit} from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from 'primeng/dynamicdialog';
import {UserInfoDTO} from '@app/shared/models';

@Component({
  selector: 'app-view-booking',
  templateUrl: './view-booking.component.html',
  styleUrls: ['./view-booking.component.css']
})
export class ViewBookingComponent implements OnInit {

  bookingDetails: UserInfoDTO;
  diffDays = 0;

  constructor(private config: DynamicDialogConfig, public ref: DynamicDialogRef) {
  }

  ngOnInit(): void {
    this.bookingDetails = this.config.data;
    if (this.bookingDetails.bookingDTO.cancellationDate){
      const date1 = new Date(this.bookingDetails.bookingDTO.cancellationDate);
      const date2 = new Date(this.bookingDetails.bookingDTO.withdrawalDate);
      const diffTime = Math.abs(date2.getTime() - date1.getTime());
      this.diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));
    }
  }

}
