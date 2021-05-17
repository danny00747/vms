export class RentDTO {
  licenseNumber: string;
  withdrawalKm: number;
  returnKm: number;
  daysDiff?: number;
  costToPay?: number;
  effectiveReturnDate: Date;
  cautionPayment: boolean;
}
