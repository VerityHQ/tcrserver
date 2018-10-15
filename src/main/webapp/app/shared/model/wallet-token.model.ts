export interface IWalletToken {
  id?: number;
  identityAddress?: string;
  token?: string;
  amount?: number;
}

export const defaultValue: Readonly<IWalletToken> = {};
