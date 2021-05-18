export class RentDTO {
  rentId: string;
  licenseNumber: string;
  withdrawalKm: number;
  returnKm: number;
  daysDiff?: number;
  costToPay?: number;
  effectiveReturnDate: Date;
  cautionPayment: boolean;
}
