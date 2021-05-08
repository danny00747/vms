import {CarDTO} from '@app/shared/models/car';

export class BookingDTO {
  bookingId: string;
  cancellationDate: Date;
  bookingState: BookingState;
  withdrawalDate: Date;
  returnDate: Date;
  carDTO: CarDTO;
}

export enum BookingState {
  Open = 'OPEN',
  Cancelled = 'CANCELLED',
  Deleted = 'DELETED',
  Finished = 'FINISHED',
}
