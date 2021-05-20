import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CreateUserDTO} from '@app/shared/models';
import {Observable} from 'rxjs';
import {BookingDTO} from '@app/shared/models/booking';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private http: HttpClient) {
  }

  createBooking(carId: string, booking: any): Observable<BookingDTO> {
    return this.http.post<BookingDTO>(`/api/v1/bookings/${carId}`, booking);
  }
}
