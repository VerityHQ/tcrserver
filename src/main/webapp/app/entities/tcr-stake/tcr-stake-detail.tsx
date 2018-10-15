import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tcr-stake.reducer';
import { ITcrStake } from 'app/shared/model/tcr-stake.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITcrStakeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TcrStakeDetail extends React.Component<ITcrStakeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tcrStakeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            TcrStake [<b>{tcrStakeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="amount">Amount</span>
            </dt>
            <dd>{tcrStakeEntity.amount}</dd>
            <dt>
              <span id="token">Token</span>
            </dt>
            <dd>{tcrStakeEntity.token}</dd>
            <dt>
              <span id="sharesIssued">Shares Issued</span>
            </dt>
            <dd>{tcrStakeEntity.sharesIssued}</dd>
            <dt>
              <span id="ownerIdentity">Owner Identity</span>
            </dt>
            <dd>{tcrStakeEntity.ownerIdentity}</dd>
          </dl>
          <Button tag={Link} to="/entity/tcr-stake" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/tcr-stake/${tcrStakeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tcrStake }: IRootState) => ({
  tcrStakeEntity: tcrStake.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TcrStakeDetail);
