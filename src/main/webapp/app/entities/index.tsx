import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Tcr from './tcr';
import TcrStake from './tcr-stake';
import WalletToken from './wallet-token';
import Identity from './identity';
import IdentityProfile from './identity-profile';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/tcr`} component={Tcr} />
      <ErrorBoundaryRoute path={`${match.url}/tcr-stake`} component={TcrStake} />
      <ErrorBoundaryRoute path={`${match.url}/wallet-token`} component={WalletToken} />
      <ErrorBoundaryRoute path={`${match.url}/identity`} component={Identity} />
      <ErrorBoundaryRoute path={`${match.url}/identity-profile`} component={IdentityProfile} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
