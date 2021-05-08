import {TownDTO} from '@app/shared/models/town';

export class AddressDTO {
  road: string;
  postBox: number;
  houseNumber: number;
  townDTO: TownDTO;
}
