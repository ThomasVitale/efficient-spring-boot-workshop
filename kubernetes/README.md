# Setting up a Kubernetes platform on DigitalOcean

This guide explains how to set up a Kubernetes platform on DigitalOcean where to run all the examples in this workshop.

## Installing the DigitalOcean CLI

Install the DigitalOcean CLI (`doctl`) as explained in the [official documentation](https://docs.digitalocean.com/reference/doctl/how-to/install/).
On macOS and Linux, you can use Homebrew.

```shell
$ brew install doctl
```

Following the documentation, create an API token to get access to Digital Ocean from `doctl`.

## Creating a Kubernetes cluster

Create a Kubernetes cluster using the Digital Ocean managed service with your choice of node size and region.

```shell
$ doctl k8s cluster create polar-cluster \
--auto-upgrade=true \
--maintenance-window "saturday=21:00" \
--node-pool "name=basicnp;size=s-2vcpu-4gb;count=3;tag=polar;label=type=basic;auto-scale=true;min-nodes=2;max-nodes=4" \
--region ams3
```

You can get a list of supported regions with the following command.

```shell
$ doctl k8s options regions
```

Regarding node sizes, you can get a complete list and information about prices with the following commands.

```shell
$ doctl k8s options sizes
$ doctl compute size list
```

You can even choose a specific Kubernetes version among the supported ones returned by the following command.

```shell
$ doctl k8s options versions
```

After creating the cluster, you can check it was correctly created with the following command.

```shell
$ doctl k8s cluster list
```

Also, verify your Kubernetes CLI is configured to work with the new cluster.

```shell
$ kubectl config current-context
```

## Configuring DNS

Make sure your custom domain name is configured to use the name servers by Digital Ocean.
Afterwards, register the domain name within Digital Ocean.

```shell
$ doctl compute domain create <domain>
```

## Installing Cert Manager

Open a Terminal window, navigate to `platform/cert-manager`, and run the following to install Cert Manager and
configure a `ClusterIssuer` resource. Make sure you replace the email in the resource with your own first.

```shell
$ kubectl apply -k .
```

## Installing Knative

One way to install and manage Knative is via the official [Operator](https://knative.dev/docs/install/operator/knative-with-operators/).

Install the Knative Operator as follows.

```shell
$ kubectl apply -f https://github.com/knative/operator/releases/download/knative-v1.0.0/operator.yaml
```

You can verify the Operator is deployed with this command.

```shell
$ kubectl get deployment knative-operator
```

Next, configure Knative by running the following command from `platform/knative`. First, make sure you add your
custom domain name in the `KnativeServing` resource.

```shell
$ kubectl apply -k .
```

Check the deployment is completed successfully.

```shell
$ kubectl get deployment -n knative-serving
```

Finally, you can find the public IP address exposed by the load balancer configured for the Kourier Ingress used by Knative.

```shell
$ kubectl --namespace knative-serving get service kourier
```
