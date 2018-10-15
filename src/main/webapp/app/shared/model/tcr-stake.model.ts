export interface ITcrStake {
  id?: number;
  amount?: number;
  token?: string;
  sharesIssued?: number;
  ownerIdentity?: string;
}

export const defaultValue: Readonly<ITcrStake> = {};
