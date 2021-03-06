import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallet-token.reducer';
import { IWalletToken } from 'app/shared/model/wallet-token.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletTokenDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WalletTokenDetail extends React.Component<IWalletTokenDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { walletTokenEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            WalletToken [<b>{walletTokenEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="identityAddress">Identity Address</span>
            </dt>
            <dd>{walletTokenEntity.identityAddress}</dd>
            <dt>
              <span id="token">Token</span>
            </dt>
            <dd>{walletTokenEntity.token}</dd>
            <dt>
              <span id="amount">Amount</span>
            </dt>
            <dd>{walletTokenEntity.amount}</dd>
          </dl>
          <Button tag={Link} to="/entity/wallet-token" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/wallet-token/${walletTokenEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ walletToken }: IRootState) => ({
  walletTokenEntity: walletToken.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletTokenDetail);
