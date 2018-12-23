package com.orange.verify.admin;

import com.orange.verify.common.rsa.RsaUtil;

public class RsaTest {

    public static void main(String[] args) throws Exception {

        String decodeRsa = RsaUtil.decodeRsa("MvH9KjTo0vF+Su5X0sDi9QMwAnJI9oKYxYC/0DkZWtUqRFUfW5pESytoZGwelehIQefsfAGLAg/KaOC882VET4klE97VVo7EV3myGP8ble7a86nxSsPKN8rJjJYFszoHnRcxa7HymbS0be27uZciYD0TcFO7kVaDwIzwxlURQZM=",
                "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAImJvnLEXlUNK6" +
                "mWifAKX61PEfE6eQpXTx+sxuvUKcVlRdeximysbdVEwlH5sS1hLjn5nTXeTD" +
                        "+Hh05qtWOQ5Qxhxv6WOuoT" +
                "DsxuLAkVhwzeShiVOB4abBFugNvQD1kWq6SZNPqISLR4+qnvDjLsAVchAgEEkDAS" +
                        "/zP9vgVvyksXAgMBAAECgYBIZG8" +
                "Qu1knjTIhJjJeXSd08jxwayWQktn7wxZExLxjwOVdHX7Nqoh5C6L4QOPIDfGl5EOiOeqKOcy3MqVczcQw0xANn4xexFw" +
                "RP6WzF5uqVUzdB7OSKJohO46pcM8D81gouwG8bn8oXpHtAzhqjtay9Nc3M66KO" +
                        "+BqkWp4HdeM2QJBANzh6JFog+ScURp0" +
                "hDkC16D0NkJCejIFbTqxErOt4RChd1Bp4IcKOXReICm43kOBLbVdKpr2D3E8S00D9G3awasCQQCfZ6cFOueLlii+gr17" +
                "78QpFqqbkNJyJ3jTqy0O7gno" +
                        "/eEO6whPg0j2gjmgXUQKNVsf8ddgx6xKnWfOAuGEFEhFAkANhLoCJD5mQHbqQpVRE+50i" +
                "F3FwmHOK+zaebnmS08KAIfX4RL" +
                        "/0M1hZN1dEFZyFTbh8bjI0SRyzrjhFsvf6VKPAkA7UFRjq9FwrUUn7noXXdY9+8Mr8" +
                "6168JkTGgTAI27olV9jL619+Lnzj+1lcI7axZPMxS6BQosnkH6Jt6S2gt2JAkBsAlo5rC" +
                "zv/h6HtfzsmUuk9P9t+LLNu0ZK9O0ftorH9lMbGncqNlhbyRPFJ9UaFumfmShLzKxdeAKFYRhoA2Yv");

        System.out.println(decodeRsa);


    }

}
