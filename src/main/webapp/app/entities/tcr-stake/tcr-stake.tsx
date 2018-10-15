import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './tcr-stake.reducer';
import { ITcrStake } from 'app/shared/model/tcr-stake.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITcrStakeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class TcrStake extends React.Component<ITcrStakeProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { tcrStakeList, match } = this.props;
    return (
      <div>
        <h2 id="tcr-stake-heading">
          Tcr Stakes
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Tcr Stake
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Amount</th>
                <th>Token</th>
                <th>Shares Issued</th>
                <th>Owner Identity</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tcrStakeList.map((tcrStake, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${tcrStake.id}`} color="link" size="sm">
                      {tcrStake.id}
                    </Button>
                  </td>
                  <td>{tcrStake.amount}</td>
                  <td>{tcrStake.token}</td>
                  <td>{tcrStake.sharesIssued}</td>
                  <td>{tcrStake.ownerIdentity}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tcrStake.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tcrStake.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tcrStake.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ tcrStake }: IRootState) => ({
  tcrStakeList: tcrStake.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TcrStake);
