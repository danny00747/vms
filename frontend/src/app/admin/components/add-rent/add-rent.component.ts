import {Component, OnInit} from '@angular/core';
import {DynamicDialogRef} from 'primeng/dynamicdialog';

@Component({
  selector: 'app-add-rent',
  templateUrl: './add-rent.component.html',
  styleUrls: ['./add-rent.component.css']
})
export class AddRentComponent implements OnInit {

  bookingId: string;
  licenseNumber: string;
  withdrawalKm: number;
  returnKm: number;
  effectiveReturnDate: Date;
  cautionPayment: boolean;

  constructor(public ref: DynamicDialogRef) {
  }

  ngOnInit(): void {
  }

}
