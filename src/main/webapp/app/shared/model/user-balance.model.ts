export interface IUserBalance {
  id?: number;
  userId?: number;
  currency?: number;
  balance?: number;
  nameLogin?: string;
  nameId?: number;
  currencyManyId?: number;
}

export class UserBalance implements IUserBalance {
  constructor(
    public id?: number,
    public userId?: number,
    public currency?: number,
    public balance?: number,
    public nameLogin?: string,
    public nameId?: number,
    public currencyManyId?: number
  ) {}
}
