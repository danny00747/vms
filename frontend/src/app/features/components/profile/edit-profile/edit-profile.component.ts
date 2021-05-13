import { Component, OnInit } from '@angular/core';
import {DynamicDialogRef} from 'primeng/dynamicdialog';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

  username: string;
  userEmail: string;
  password: string;
  phoneNumber: number;

  road: string;
  houseNumber: number;
  townName: string;
  postCode: number;

  constructor(public ref: DynamicDialogRef) { }

  ngOnInit(): void {
  }

}
