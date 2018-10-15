import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Identity from './identity';
import IdentityDetail from './identity-detail';
import IdentityUpdate from './identity-update';
import IdentityDeleteDialog from './identity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IdentityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IdentityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IdentityDetail} />
      <ErrorBoundaryRoute path={match.url} component={Identity} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={IdentityDeleteDialog} />
  </>
);

export default Routes;
