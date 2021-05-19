import {RoleDTO} from './role';
import {AddressDTO} from '@app/shared/models/address';
import {BookingDTO} from '@app/shared/models/booking';

export interface LoginDTO {
  pseudo: string;
  password: string;
}

export interface CreateUserDTO {
  username: string;
  userEmail: string;
  password: string;
  phoneNumber: string;
  addressDTO: AddressDTO;
}

export class UserInfoDTO {
  userId: string;
  username: any;
  userEmail: string;
  phoneNumber: string;
  userRoles: RoleDTO[];
  activated: boolean;
  password?: string;
  createdAt: Date;
  addressDTO: AddressDTO;
  bookingDTO: BookingDTO;
}

export interface UserDTO {
  id: number;
  firstName: string;
  lastName: string;
  password: string;
  birthdate?: string;
  email: string;
  roleId: number;
  role: RoleDTO;
  isActive: boolean;
}

export interface JWT {
  message: string;
  token: string;
}
