import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import tcr, {
  TcrState
} from 'app/entities/tcr/tcr.reducer';
// prettier-ignore
import tcrStake, {
  TcrStakeState
} from 'app/entities/tcr-stake/tcr-stake.reducer';
// prettier-ignore
import walletToken, {
  WalletTokenState
} from 'app/entities/wallet-token/wallet-token.reducer';
// prettier-ignore
import identity, {
  IdentityState
} from 'app/entities/identity/identity.reducer';
// prettier-ignore
import identityProfile, {
  IdentityProfileState
} from 'app/entities/identity-profile/identity-profile.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly tcr: TcrState;
  readonly tcrStake: TcrStakeState;
  readonly walletToken: WalletTokenState;
  readonly identity: IdentityState;
  readonly identityProfile: IdentityProfileState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  tcr,
  tcrStake,
  walletToken,
  identity,
  identityProfile,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
