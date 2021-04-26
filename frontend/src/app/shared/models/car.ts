import {ModelDTO} from '@app/shared/models/model';

export class CarDTO {
  carId: string;
  madeInYear: number;
  isDamaged: boolean;
  purchasedPrice: number;
  licensePlate: string;
  modelDTO: ModelDTO;
}
