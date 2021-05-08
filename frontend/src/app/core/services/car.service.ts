import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CarDTO} from '@app/shared/models';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private http: HttpClient) {
  }


  getCars(date: string = new Date().toISOString()): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(`/api/v1/cars?withdrawalDate=${date}`);
  }

  getCarsByBrand(brand: string): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(`/api/v1/cars?brand=${brand}`);
  }

  getOneCar(carId: string): Observable<CarDTO> {
    return this.http.get<CarDTO>(`/api/v1/cars/${carId}`);
  }

  getNoneBookedCars(withdrawalDate: string): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(`/api/v1/cars?withdrawalDate=${withdrawalDate}`);
  }

}
