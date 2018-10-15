import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './identity-profile.reducer';
import { IIdentityProfile } from 'app/shared/model/identity-profile.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIdentityProfileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class IdentityProfileDetail extends React.Component<IIdentityProfileDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { identityProfileEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            IdentityProfile [<b>{identityProfileEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="firstName">First Name</span>
            </dt>
            <dd>{identityProfileEntity.firstName}</dd>
            <dt>
              <span id="lastName">Last Name</span>
            </dt>
            <dd>{identityProfileEntity.lastName}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{identityProfileEntity.email}</dd>
            <dt>
              <span id="redditUrl">Reddit Url</span>
            </dt>
            <dd>{identityProfileEntity.redditUrl}</dd>
          </dl>
          <Button tag={Link} to="/entity/identity-profile" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/identity-profile/${identityProfileEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ identityProfile }: IRootState) => ({
  identityProfileEntity: identityProfile.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(IdentityProfileDetail);
