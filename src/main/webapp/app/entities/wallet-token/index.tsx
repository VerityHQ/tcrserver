import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WalletToken from './wallet-token';
import WalletTokenDetail from './wallet-token-detail';
import WalletTokenUpdate from './wallet-token-update';
import WalletTokenDeleteDialog from './wallet-token-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WalletTokenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WalletTokenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WalletTokenDetail} />
      <ErrorBoundaryRoute path={match.url} component={WalletToken} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={WalletTokenDeleteDialog} />
  </>
);

export default Routes;
