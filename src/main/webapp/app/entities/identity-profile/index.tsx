import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IdentityProfile from './identity-profile';
import IdentityProfileDetail from './identity-profile-detail';
import IdentityProfileUpdate from './identity-profile-update';
import IdentityProfileDeleteDialog from './identity-profile-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IdentityProfileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IdentityProfileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IdentityProfileDetail} />
      <ErrorBoundaryRoute path={match.url} component={IdentityProfile} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={IdentityProfileDeleteDialog} />
  </>
);

export default Routes;
