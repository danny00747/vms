import {Component, Input, OnInit} from '@angular/core';
import {UserInfoDTO} from '@app/shared/models';

@Component({
  selector: 'app-edit-cars-list',
  templateUrl: './edit-cars-list.component.html',
  styleUrls: ['./edit-cars-list.component.css']
})
export class EditCarsListComponent implements OnInit {

  @Input() users: UserInfoDTO[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
