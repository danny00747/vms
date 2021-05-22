import { Component, OnInit } from '@angular/core';
import {CarDTO} from '@app/shared/models';
import {CarService} from '@app/core/services/car.service';
import {EToastSeverities, ToastService} from '@app/core/services';

@Component({
  selector: 'app-car-maintenance',
  templateUrl: './car-maintenance.component.html',
  styleUrls: ['./car-maintenance.component.css']
})
export class CarMaintenanceComponent implements OnInit {

  sourceCars: CarDTO[] = [];

  targetCars: CarDTO[] = [];

  constructor( private carService: CarService,
               public toastService: ToastService) { }

  ngOnInit(): void {
    this.getAllCars();
  }

  getAllCars(): void {
    this.carService.getAllCars()
      .subscribe(
         (data: CarDTO[]) => {
          this.sourceCars = data;
          this.targetCars = data.filter(car => car.isDamaged);
        },
        error => {
          console.error(error);
        });
  }

  submit(): void {
    const damagedCars: Array<string> = this.targetCars.map(car => car.carId);
    this.carService.saveDamagedCars(damagedCars)
      .subscribe(
        () => {
          this.toastService.show(EToastSeverities.SUCCESS, 'Saved !');
        },
        error => {
          console.error(error);
        });
  }
}
