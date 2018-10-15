import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Tcr from './tcr';
import TcrDetail from './tcr-detail';
import TcrUpdate from './tcr-update';
import TcrDeleteDialog from './tcr-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TcrUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TcrUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TcrDetail} />
      <ErrorBoundaryRoute path={match.url} component={Tcr} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TcrDeleteDialog} />
  </>
);

export default Routes;
