import {Role, RoleDTO} from './role';

export interface LoginDTO {
  pseudo: string;
  password: string;
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

export interface CreateUserDTO {
  firstName: string;
  lastName: string;
  password: string;
  email: string;
  roleId: number;
  isActive: boolean;
}

export interface UpdateUserDTO {
  firstName: string;
  lastName: string;
  password: string;
  email: string;
  roleId: number;
  isActive: boolean;
}

export interface SimpleUserDTO {
  id: number;
}

export interface JWT {
  message: string;
  access_token: string;
}

export interface IUser {
  firstName: string;
  lastName: string;
  email: string;
  role: Role;
  token?: string;
  id?: string;
  password?: string;
}

export class UserImpl implements IUser {
  constructor(
    public firstName: string,
    public lastName: string,
    public email: string,
    public role: Role,
    public password?: string,
  ) {
    this.firstName = firstName ? firstName : null;
    this.lastName = lastName ? lastName : null;
    this.email = email ? email : null;
    this.password = password ? password : null;
    this.role = role ? role : null;
  }
}
