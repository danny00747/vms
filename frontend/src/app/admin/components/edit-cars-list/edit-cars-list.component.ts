import {Component, Input, OnInit} from '@angular/core';
import {CarDTO, UserInfoDTO} from '@app/shared/models';
import {SelectItem} from 'primeng/api';
import {CarService} from '@app/core/services/car.service';
import {takeUntil} from 'rxjs/operators';

@Component({
  selector: 'app-edit-cars-list',
  templateUrl: './edit-cars-list.component.html',
  styleUrls: ['./edit-cars-list.component.css']
})
export class EditCarsListComponent implements OnInit {

  @Input() users: UserInfoDTO[] = [];
  cars: CarDTO[] = [];
  sortOptions: SelectItem[];
  rating = 3;

  sortKey: string;

  sortField: string;

  sortOrder: number;


  constructor( private carService: CarService) { }

  ngOnInit(): void {
    this.getAllCars();
    this.sortOptions = [
      {label: 'Price High to Low', value: '!price'},
      {label: 'Price Low to High', value: 'price'}
    ];
  }

  getAllCars(): void {
    this.carService.getAllCars()
      .subscribe(
        async (data: CarDTO[]) => {
          this.cars = data;
        },
        error => {
          console.error(error);
        });
  }

  onSortChange(event): void {
    const value = event.value;

    if (value.indexOf('!') === 0) {
      this.sortOrder = -1;
      this.sortField = 'modelDTO.princingDetailsDTO.costPerDay';
    }
    else {
      this.sortOrder = 1;
      this.sortField = 'modelDTO.princingDetailsDTO.costPerDay';
    }
  }

}
