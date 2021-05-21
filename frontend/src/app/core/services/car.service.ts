import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CarDTO, ModelDTO} from '@app/shared/models';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private http: HttpClient) {
  }

  getAllCars(): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(`/api/v1/admin/cars`);
  }

  getCars(): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(`/api/v1/cars`);
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

  getAllCarsModels(): Observable<ModelDTO[]> {
    return this.http.get<ModelDTO[]>(`/api/v1/models`);
  }

}
