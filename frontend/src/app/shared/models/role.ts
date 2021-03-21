export interface RoleDTO {
  id: number;
  label: string;
  Role: Role;
}

export enum Role {
  Dev = 'dev',
  Admin = 'admin',
  User = 'user'
}
