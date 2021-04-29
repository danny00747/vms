import {ModelOptionDTO} from '@app/shared/models/modelOption';
import {PrincingDetailsDTO} from '@app/shared/models/princingDetails';

export class ModelDTO {
  modelId: string;
  modelType: string;
  brand: string;
  modelOptionDTO: ModelOptionDTO;
  princingDetailsDTO: PrincingDetailsDTO;
}
