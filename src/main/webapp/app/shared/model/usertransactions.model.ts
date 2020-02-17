import { Action } from 'app/shared/model/enumerations/action.model';

export interface IUsertransactions {
  id?: number;
  from_quantity?: number;
  toQuantity?: number;
  action?: Action;
  fromId?: number;
  toId?: number;
  userLogin?: string;
  userId?: number;
}

export class Usertransactions implements IUsertransactions {
  constructor(
    public id?: number,
    public from_quantity?: number,
    public toQuantity?: number,
    public action?: Action,
    public fromId?: number,
    public toId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
