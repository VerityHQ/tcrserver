import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TcrStake from './tcr-stake';
import TcrStakeDetail from './tcr-stake-detail';
import TcrStakeUpdate from './tcr-stake-update';
import TcrStakeDeleteDialog from './tcr-stake-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TcrStakeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TcrStakeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TcrStakeDetail} />
      <ErrorBoundaryRoute path={match.url} component={TcrStake} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TcrStakeDeleteDialog} />
  </>
);

export default Routes;
