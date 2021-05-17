import { Component, OnInit } from '@angular/core';
import {CarDTO} from '@app/shared/models';
import {CarService} from '@app/core/services/car.service';

@Component({
  selector: 'app-car-maintenance',
  templateUrl: './car-maintenance.component.html',
  styleUrls: ['./car-maintenance.component.css']
})
export class CarMaintenanceComponent implements OnInit {

  sourceCars: CarDTO[] = [];

  targetCars: CarDTO[] = [];

  constructor( private carService: CarService) { }

  ngOnInit(): void {
    this.getAllCars();
  }


  getAllCars(): void {
    this.carService.getAllCars()
      .subscribe(
        async (data: CarDTO[]) => {
          this.sourceCars = data;
        },
        error => {
          console.error(error);
        });
  }

}
