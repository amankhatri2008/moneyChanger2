export interface IUserbalance {
  id?: number;
  balance?: number;
  nameLogin?: string;
  nameId?: number;
  currencyManyId?: number;
}

export class Userbalance implements IUserbalance {
  constructor(
    public id?: number,
    public balance?: number,
    public nameLogin?: string,
    public nameId?: number,
    public currencyManyId?: number
  ) {}
}
