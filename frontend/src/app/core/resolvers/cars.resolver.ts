import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {CarDTO} from '@app/shared/models';
import {CarService} from '@app/core/services/car.service';


@Injectable({
  providedIn: 'root'
})
export class CarsResolver implements Resolve<CarDTO[]> {

  constructor(private http: HttpClient,
              private carService: CarService) {
  }

  resolve(): any {
    return this.carService.getCars();
  }
}
