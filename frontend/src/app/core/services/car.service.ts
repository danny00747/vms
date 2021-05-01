import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {CarDTO, JWT, LoginDTO, UserDTO} from '@app/shared/models';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  status: string[] = ['OUTOFSTOCK', 'INSTOCK', 'LOWSTOCK'];

  productNames: string[] = [
    'Bamboo Watch',
    'Black Watch',
    'Blue Band',
    'Blue T-Shirt',
    'Bracelet',
    'Brown Purse',
    'Chakra Bracelet',
    'Galaxy Earrings',
    'Game Controller',
    'Gaming Set',
    'Gold Phone Case',
    'Green Earbuds',
    'Green T-Shirt',
    'Grey T-Shirt',
    'Headphones',
    'Light Green T-Shirt',
    'Lime Band',
    'Mini Speakers',
    'Painted Phone Case',
    'Pink Band',
    'Pink Purse',
    'Purple Band',
    'Purple Gemstone Necklace',
    'Purple T-Shirt',
    'Shoes',
    'Sneakers',
    'Teal T-Shirt',
    'Yellow Earbuds',
    'Yoga Mat',
    'Yoga Set',
  ];


  constructor(private http: HttpClient) {
  }


  getCars(): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>('/api/v1/cars');
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

  getUserByJwt(): Observable<UserDTO> {
    return this.http.get<UserDTO>(`/api/v1/account`);
  }

}
