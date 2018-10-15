import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './identity.reducer';
import { IIdentity } from 'app/shared/model/identity.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIdentityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class IdentityDetail extends React.Component<IIdentityDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { identityEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Identity [<b>{identityEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="chain">Chain</span>
            </dt>
            <dd>{identityEntity.chain}</dd>
            <dt>
              <span id="identityAddress">Identity Address</span>
            </dt>
            <dd>{identityEntity.identityAddress}</dd>
          </dl>
          <Button tag={Link} to="/entity/identity" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/identity/${identityEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ identity }: IRootState) => ({
  identityEntity: identity.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(IdentityDetail);
