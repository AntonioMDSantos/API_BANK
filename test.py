import json
import requests
import unittest
from uuid import UUID

URL = 'http://localhost:4567/api/v1'
HEADERS = {'Content-Type': 'application/json'}

CONTAS = [
    dict(email='antonio@example.com', saldo=500),
    dict(email='thais@example.com', saldo=500),
    ]

def is_uuid(s):
    try:
        UUID(s)
        return True
    except ValueError:
        return False

class TestEndpoint(unittest.TestCase):

    def test_api_endpoint(self):
        res = requests.get(URL, headers=HEADERS)
        self.assertEqual(404, res.status_code)

    def test_invalid_enpoint(self):
        res = requests.get(URL + '/foo', headers=HEADERS)
        self.assertEqual(404, res.status_code)

class TestAccount(unittest.TestCase):

    def test_empty_account(self):
        res = requests.post(URL + '/contas', headers=HEADERS).json()
        self.assertEqual(res.get('status'), 400)

    def test_valid_account(self):
        account = requests.post(URL + '/contas', data=json.dumps(CONTAS[0]), headers=HEADERS).json()
        self.assertTrue(is_uuid(account.get('uuid')))

class TestTransfer(unittest.TestCase):

    def test_empty_transfer(self):
        res = requests.post(URL + '/transferencia', headers=HEADERS).json()
        self.assertEqual(res.get('status'), 400)

    def test_invalid_transfer(self):
        data = {"from": "foo", "to": "bar", "amount": 100}
        res = requests.post(URL + '/transferencia', data=json.dumps(data), headers=HEADERS).json()
        self.assertEqual(res.get('status'), 404)

    def test_valid_transfer(self):
        acc1 = requests.post(URL + '/contas', data=json.dumps(CONTAS[0]), headers=HEADERS).json()
        self.assertTrue(is_uuid(acc1.get('uuid')))
        acc2 = requests.post(URL + '/contas', data=json.dumps(CONTAS[0]), headers=HEADERS).json()
        self.assertTrue(is_uuid(acc2.get('uuid')))
        transfer_data = {"from": acc1.get('uuid'), "to": acc2.get('uuid'), "amount": 100}
        transfer = requests.post(URL + '/transferencia', data=json.dumps(transfer_data), headers=HEADERS).json()
        self.assertTrue(is_uuid(transfer.get('uuid')))
        acc1_after = requests.get(URL + '/contas/' + acc1.get('uuid'), headers=HEADERS).json()
        self.assertEqual(acc1.get('uuid'), acc1_after.get('uuid'))
        self.assertEqual(acc1.get('saldo'), acc1_after.get('saldo') + 100)

if __name__ == '__main__':
    unittest.main()
