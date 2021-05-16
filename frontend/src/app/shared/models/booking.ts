import {CarDTO} from '@app/shared/models/car';
import {RentDTO} from '@app/shared/models/rent';

export class BookingDTO {
  bookingId: string;
  cancellationDate: Date;
  bookingState: BookingState;
  withdrawalDate: Date;
  returnDate: Date;
  createdAt?: Date;
  carDTO: CarDTO;
  rentDTO: RentDTO;
}

export enum BookingState {
  Open = 'OPEN',
  Cancelled = 'CANCELLED',
  Deleted = 'DELETED',
  Finished = 'FINISHED',
}
